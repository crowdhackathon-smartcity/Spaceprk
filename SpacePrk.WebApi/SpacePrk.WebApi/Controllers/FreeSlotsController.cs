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
        public IActionResult InsertParkingSpace([FromBody]PostFreeSpaceRequest request)
        {
            if (request == null)
            {
                return BadRequest();
            }

            var freeSpaces = _prkSpaceService.InsertParkingSpace(request);

            return new JsonResult(freeSpaces);
        }

        //// GET api/values/5
        //[HttpGet("{id}")]
        //public string Get(int id)
        //{
        //    return "value";
        //}

        //// POST api/values
        //[HttpPost]
        //public void Post([FromBody]string value)
        //{
        //}

        //// PUT api/values/5
        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody]string value)
        //{
        //}

        //// DELETE api/values/5
        //[HttpDelete("{id}")]
        //public void Delete(int id)
        //{
        //}
    }
}