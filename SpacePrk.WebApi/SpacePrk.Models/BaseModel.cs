using System;

namespace SpacePrk.Models
{
    public class BaseModel
    {
        public bool IsActive { get; set; } = true;
        public DateTime DateCreated { get; set; } = DateTime.Now;
        public DateTime? DateModified { get; set; }
        public DateTime? DateDeleted { get; set; }
    }
}
