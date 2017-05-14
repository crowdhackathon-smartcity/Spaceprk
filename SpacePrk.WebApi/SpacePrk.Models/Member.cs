using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace SpacePrk.Models
{
    public class Member : BaseModel
    {
        public int Memberid { get; set; }
        [MaxLength(128)]
        public string AspNetUserId { get; set; }
        [MaxLength(50)]
        public string FirstName { get; set; }
        [MaxLength(50)]
        public string LastName { get; set; }
        public DateTime? BirthDate { get; set; }

        public IEnumerable<MemberCar> MemberCars { get; set; }
    }
}
