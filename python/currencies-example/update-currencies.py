import sys
import os
import requests

polydyne_base_url = 'https://api.polydyne.com/v1'
oxr_base_url = 'https://openexchangerates.org/api'

# Helper message for fatal errors
def err_exit(msg):
    print(msg)
    exit(1)

# Gets PolyDyne API credentials from environment variables
def polydyne_credentials():
    try:
        user = os.environ['USERNAME']
    except KeyError:
        err_exit('USERNAME environment variable not defined')
    try:
        password = os.environ['PASSWORD']
    except KeyError:
        err_exit('PASSWORD environment variable not defined')
    return user, password

# Gets App ID for openexchangerates.org from environment variable
def get_oxr_app_id():
    try:
        return os.environ['APP_ID']
    except KeyError:
        err_exit('APP_ID environment variable not defined')

# Fetches most recent exchange rates from openexchangerates.org
def get_rates(app_id):
    response = requests.get(oxr_base_url + '/latest.json', {'app_id': app_id})
    if response.status_code == 401:
        err_exit('Unable to access openexchangerates.org. Please check your app_id')
    elif response.status_code != 200:
        err_exit('Unknown error accessing openexchangerates.org')
    else:
        return response.json()['rates']

# Get all pages of currencies
def get_currencies(creds):
    url = polydyne_base_url + '/currencies'
    response = requests.get(url, auth = creds)
    if response.status_code != 200:
        err_exit('Failed to fetch currencies from polydyne API')
    else:
        return response.json()

# Create objects to submit in batch update
def build_batch_update(updates):
    for id, rate in updates.items():
        yield {
            'method': 'PATCH',
            'resource': '/currencies/' + str(id),
            'body': {
                'exchangeRate': rate
            }
        }

# Execute the batch update
def do_batch_update(batch, creds):
    response = requests.post(polydyne_base_url + '/batch', auth = creds, json = batch)
    if response.status_code != 200:
        err_exit('Unable to update currencies')

if __name__ == "__main__":

    # Get credentials
    creds = polydyne_credentials()
    app_id = get_oxr_app_id()

    # Fetch current exchange rates using PolyDyne API
    currencies = get_currencies(creds)

    # Fetch current exchange rates from openexchangerates.org
    rates = get_rates(app_id)

    # Determine which currencies need to be updated
    updates = {}  # Dictionary of currencyId -> new exchange rate (if found)
    for currency in currencies:
        code = currency['currencyCode']
        if code in rates:
            currentRate = currency['exchangeRate']
            newRate = rates[code]
            if currentRate == newRate:
                print('Skipping', code)
            else:
                print('Updating', code, currentRate, '->', newRate)
                updates[currency['currencyId']] = rates[currency['currencyCode']]
        else:
            print('Ignoring', code)

    # Update all currencies in a batch
    batch = list(build_batch_update(updates))
    do_batch_update(batch, creds)


