namespace SpacePrk.Models
{
    public class ParkingSpace
    {
        public int ParkingSpaceId { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public bool IsFree { get; set; } = true;
        public float AvailableSpace { get; set; }
        public bool? IsDisabilitySpace { get; set; }
    }
}
