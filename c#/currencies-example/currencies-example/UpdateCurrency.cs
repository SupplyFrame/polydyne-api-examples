using System;
using RestSharp;
using RestSharp.Authenticators;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Collections;
using System.Collections.Generic;

namespace CurrenciesExample
{
    public class UpdateCurrency
    {
        private static Dictionary<String, Double> currentExchangeRateMap = new Dictionary<String, Double>();
        private static Dictionary<String, Double> currencyCodeAndRateMap = new Dictionary<String, Double>();
        private static Hashtable currencyCodeAndIdMap = new Hashtable();
        private static ArrayList currencyList = new ArrayList();

        public void GetExchangeRates()
        {
            // Fetch latest exchange rates from the open exchange rate API
            String url = Start.OPEN_EXCHANGE_URL + "/latest.json?app_id=" + Start.APP_ID;
            var client = new RestClient();
            client.BaseUrl = new System.Uri(url);
            var request = new RestRequest();
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute(request);
            // Deserialize json and store in a dictionary
            RestSharp.Deserializers.JsonDeserializer json = new RestSharp.Deserializers.JsonDeserializer();
            var jsonObj = json.Deserialize<Dictionary<String, String>>(response);
            // Obtain the jsonObject rates
            var ans = jsonObj["rates"];
            // Parse the rates information to a jsonObject and store in a dictionary mapping currency code to latest exchange rates
            JObject jsonObject = JObject.Parse(ans);
            currentExchangeRateMap = jsonObject.ToObject<Dictionary<String, Double>>();
        }

        public void GetCurrency()
        {
            String url = Start.API_BASE_URL + "/currencies";
            var client = new RestClient();
            client.BaseUrl = new System.Uri(url);
            client.Authenticator = new HttpBasicAuthenticator(Start.USERNAME, Start.PASSWORD);
            var request = new RestRequest();
            request.RequestFormat = DataFormat.Json;
            var response = client.Execute(request);
            String json = response.Content.ToString();
            List<Currency> deserializedCurrency = JsonConvert.DeserializeObject<List<Currency>>(json);
            foreach (Currency currency in deserializedCurrency)
            {
                // Map each currency code to currency rate
                currencyCodeAndRateMap.Add(currency.currencyCode, currency.exchangeRate);
                // Map each currency code to currencyId
                currencyCodeAndIdMap.Add(currency.currencyCode, currency.currencyId);
            }
        }

        public void UpdateCurrencyRates()
        {
            // Use the maps to find a list of currencies to be updates
            foreach (KeyValuePair<String, Double> entry in currencyCodeAndRateMap)
            {
                String key = entry.Key;

                if (currentExchangeRateMap.ContainsKey(key))
                {
                    if (currentExchangeRateMap[key].CompareTo(currencyCodeAndRateMap[key]) != 0)
                    {
                        // Add currencies with new exchange rates
                        Currency currency = new Currency();
                        currency.currencyId = (long)currencyCodeAndIdMap[key];
                        currency.currencyCode = key;
                        currency.exchangeRate = currentExchangeRateMap[key];
                        currencyList.Add(currency);
                    }
                    else
                    {
                        Console.WriteLine("Skipping " + entry.Key);
                    }
                }
                else
                {
                    Console.WriteLine("Ignoring " + entry.Key);
                }
            }

            // Send list of currencies to be updated via batch API
            CurrencyService.UpdateCurrencies(currencyList);
        }
    }

}
