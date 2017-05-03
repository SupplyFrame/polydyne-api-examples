using System;
namespace CurrenciesExample
{
    public class BatchRequest
    {
        public String method { get; set; }
        public String resource { get; set; }
        public Object body { get; set; }
    }
}
