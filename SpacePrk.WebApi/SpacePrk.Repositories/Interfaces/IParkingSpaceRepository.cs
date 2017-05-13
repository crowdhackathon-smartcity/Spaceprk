using SpacePrk.Models;
using SpacePrk.Models.Contracts;
using System.Collections.Generic;

namespace SpacePrk.Repositories.Interfaces
{
    public interface IParkingSpaceRepository
    {
        IEnumerable<ParkingSpace> GetAvailablePrkSpacesBySize(int minSize, int maxSize);
        IEnumerable<ParkingSpace> GetAvailablePrkSpacesByLocation(ParkingSpaceRequest request);
        IEnumerable<ParkingSpace> GetAllAvailablePrkSpaces();
        bool InsertParkingSpace(PostFreeSpaceRequest request);
    }
}
