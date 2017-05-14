using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using SpacePrk.Services.Interfaces;
using System.Linq;
using SpacePrk.Models.Contracts;

namespace SpacePrk.Controllers
{
    [Route("api/[controller]")]
    public class FreeSlotsController : Controller
    {
        private IParkingSpaceService _prkSpaceService;

        public FreeSlotsController(IParkingSpaceService prkSpaceService)
        {
            _prkSpaceService = prkSpaceService;
        }

        // GET api/values
        [HttpGet]
        public IActionResult Get()
        {
            var allSpaces = _prkSpaceService.GetAllAvailablePrkSpaces();

            if (!allSpaces.Any())
                return NotFound();

            return new JsonResult(allSpaces.ToList());
        }

        [HttpPost]
        public IActionResult GetByCarSize([FromBody]ParkingSpaceRequest request)
        {
            if (request == null)
            {
                return BadRequest();
            }

            var freeSpaces = _prkSpaceService.GetAvailablePrkSpacesByCarType(request);

            return new JsonResult(freeSpaces);
        }

        [HttpPost("insert")]
        public IActionResult InsertParkingSpace([FromBody]List<PostFreeSpaceRequest> request)
        {
            if (request == null)
            {
                return BadRequest();
            }

            var freeSpaces = _prkSpaceService.InsertParkingSpace(request);

            return new JsonResult(freeSpaces);
        }

        [HttpGet("disability")]
        public IActionResult GetDisabilitySpaces()
        {
            var spaces = _prkSpaceService.GetDisabilitySpaces();

            if (!spaces.Any())
                return NotFound();

            return new JsonResult(spaces.ToList());
        }
    }
}