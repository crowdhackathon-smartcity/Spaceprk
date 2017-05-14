using SpacePrk.Services.Interfaces;
using System;
using SpacePrk.Models;
using SpacePrk.Models.Contracts;
using System.Collections.Generic;
using SpacePrk.Repositories.Interfaces;
using SpacePrk.Helpers;

namespace SpacePrk.Services
{
    public class ParkingSpaceService : IParkingSpaceService
    {
        private IParkingSpaceRepository _prkSpaceRepo;

        public ParkingSpaceService(IParkingSpaceRepository prkSpaceRepo)
        {
            _prkSpaceRepo = prkSpaceRepo;
        }

        public IEnumerable<ParkingSpace> GetAllAvailablePrkSpaces()
        {
            var freeSpaces = _prkSpaceRepo.GetAllAvailablePrkSpaces();
            return freeSpaces;
        }

        public IEnumerable<ParkingSpace> GetAvailablePrkSpacesByCarType(ParkingSpaceRequest request)
        {
            IEnumerable<ParkingSpace> freeSpaces = null;
            List<ParkingSpace> freeSpacesToReturn = new List<ParkingSpace>();

            switch (request.CarType)
            {
                case 1://(int)CarType.Small:
                    freeSpaces = _prkSpaceRepo.GetAvailablePrkSpacesBySize(Constants.SMALL_CAR_MIN_SIZE, Constants.SMALL_CAR_MAX_SIZE);
                    break;
                case 2://(int)CarType.Medium:
                    freeSpaces = _prkSpaceRepo.GetAvailablePrkSpacesBySize(Constants.MEDIUM_CAR_MIN_SIZE, Constants.MEDIUM_CAR_MAX_SIZE);
                    break;
                case 3://(int)CarType.Big:
                    freeSpaces = _prkSpaceRepo.GetAvailablePrkSpacesBySize(Constants.BIG_CAR_MIN_SIZE, Constants.BIG_CAR_MAX_SIZE);
                    break;

            }

            if (freeSpaces == null)
                return null;

            foreach (var space in freeSpaces)
            {
                var fromDatabase = new Position() { Latitude = space.Latitude, Longitude = space.Longitude };
                var fromRequest = new Position() { Latitude = request.Latitude, Longitude = request.Longitude };

                var dist = Distance(fromRequest, fromDatabase);

                if (dist <= request.Range)
                    freeSpacesToReturn.Add(space);
            }

            return freeSpacesToReturn;
        }

        public bool InsertParkingSpace(List<PostFreeSpaceRequest> request)
        {
            var success = _prkSpaceRepo.InsertParkingSpace(request);

            return (success) ? true : false;
        }

        public IEnumerable<ParkingSpace> GetDisabilitySpaces()
        {
            var spaces = _prkSpaceRepo.GetDisabilitySpaces();

            return spaces;
        }

        #region PrivateMethods
        private double ToRadian(double val)
        {
            return (Math.PI / 180) * val;
        }

        private double Distance(Position pos1, Position pos2, DistanceType type = DistanceType.Kilometers)
        {
            double R = (type == DistanceType.Miles) ? 3960 : 6378137; // 6318137 in meters

            double dLat = ToRadian(pos2.Latitude - pos1.Latitude);
            double dLon = ToRadian(pos2.Longitude - pos1.Longitude);

            double a = Math.Sin(dLat / 2) * Math.Sin(dLat / 2) +
            Math.Cos(this.ToRadian(pos1.Latitude)) * Math.Cos(this.ToRadian(pos2.Latitude)) *
            Math.Sin(dLon / 2) * Math.Sin(dLon / 2);

            double c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
            double d = R * c;

            return d;
        }
        #endregion
    }
}