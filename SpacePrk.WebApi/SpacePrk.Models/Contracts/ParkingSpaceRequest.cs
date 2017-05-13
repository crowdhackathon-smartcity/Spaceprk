using SpacePrk.Helpers;

namespace SpacePrk.Models.Contracts
{
    public class ParkingSpaceRequest
    {
        public /*CarType*/ int CarType { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public int Range { get; set; }
    }
}