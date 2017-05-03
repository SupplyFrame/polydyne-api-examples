using System;
using System.Collections;
using RestSharp;
using RestSharp.Authenticators;
namespace CurrenciesExample
{
    public static class CurrencyService
    {
        public static void UpdateCurrencies(ArrayList currencyList)
        {

            ArrayList updatedCurrencyList = new ArrayList();
            ArrayList currencyBatches = SplitIntoBatches(currencyList, 100);
            foreach (ArrayList batch in currencyBatches)
            {

                BatchRequest[] batchRequest = new BatchRequest[batch.Count];
                for (int i = 0; i < batch.Count; i++)
                {
                    batchRequest[i] = new BatchRequest();
                    batchRequest[i].method = "PATCH";
                    Currency c = (Currency)batch[i];
                    batchRequest[i].resource = "/currencies/" + c.currencyId;
                    batchRequest[i].body = c;
                }
                var client = new RestClient();
                client.BaseUrl = new System.Uri(Start.API_BASE_URL + "/batch");
                client.Authenticator = new HttpBasicAuthenticator(Start.USERNAME, Start.PASSWORD);
                var request = new RestRequest(Method.POST);
                request.RequestFormat = DataFormat.Json;
                request.AddBody(batchRequest);

                var response = client.Execute(request);
                if ((int)response.StatusCode != 200)
                {
                    Console.WriteLine("Error updating currencies");
                }
                else
                {
                    Console.WriteLine("Updated currencies");
                }

            }

        }

        private static ArrayList SplitIntoBatches(ArrayList list, int batchSize)
        {
            ArrayList result = new ArrayList();
            for (int i = 0; i < list.Count; i += batchSize)
            {
                result.Add(list.GetRange(i, Math.Min(i + batchSize, list.Count)));
            }
            return result;

        }
    }
}
