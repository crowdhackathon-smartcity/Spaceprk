namespace SpacePrk.Models
{
    public class MemberCar : BaseModel
    {
        public int MemberCarId { get; set; }
        public byte CarType { get; set; }

        public int MemberId { get; set; }
        public Member Member { get; set; }
    }
}
