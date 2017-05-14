using System.ComponentModel.DataAnnotations;

namespace SpacePrk.WebApi.Resources
{
    public class RegisterViewModel
    {
        [Required]
        [EmailAddress]
        public string Email { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "The {0} must be at least {2} and at max {1} characters long.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Compare("Password", ErrorMessage = "The password and confirmation password do not match.")]
        public string ConfirmPassword { get; set; }

        public string RoleType { get; set; } = "Member"; // Temporary we only have Member role
        public string FirstName { get; set; }
        public string LastName { get; set; }

    }
}
