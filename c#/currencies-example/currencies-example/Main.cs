using System;

namespace CurrenciesExample
{
    public class Start
    {
        public static String OPEN_EXCHANGE_URL = "https://openexchangerates.org/api";
        public static String API_BASE_URL = "https://api.polydyne.com/v1";
        public static String USERNAME;
        public static String PASSWORD;
        public static String APP_ID;

        static void Main(string[] args)
        {
            USERNAME = Environment.GetEnvironmentVariable("username");
            PASSWORD = Environment.GetEnvironmentVariable("password");
            APP_ID = Environment.GetEnvironmentVariable("app_id");

            UpdateCurrency updateCurrency = new UpdateCurrency();
            // Get latest exchange rates
            updateCurrency.GetExchangeRates();
            // Get currencies
            updateCurrency.GetCurrency();
            // Update currency exchange rates
            updateCurrency.UpdateCurrencyRates();
        }
    }
}
