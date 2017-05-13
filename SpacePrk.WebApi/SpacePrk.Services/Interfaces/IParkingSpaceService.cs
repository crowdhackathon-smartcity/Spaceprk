using SpacePrk.Models;
using SpacePrk.Models.Contracts;
using System;
using System.Collections.Generic;
using System.Text;

namespace SpacePrk.Services.Interfaces
{
    public interface IParkingSpaceService
    {
        IEnumerable<ParkingSpace> GetAvailablePrkSpacesByCarType(ParkingSpaceRequest request);
        IEnumerable<ParkingSpace> GetAllAvailablePrkSpaces();
    }
}
