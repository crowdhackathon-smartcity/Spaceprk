using System;
using System.Collections.Generic;
using System.Text;

namespace SpacePrk.Models.Contracts
{
    public class PostFreeSpaceRequest
    {
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public float SpaceLength { get; set; }
        public int SensorId { get; set; }
    }
}
