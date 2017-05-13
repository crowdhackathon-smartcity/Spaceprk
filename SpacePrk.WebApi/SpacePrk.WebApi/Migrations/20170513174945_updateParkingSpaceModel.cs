using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace SpacePrk.WebApi.Migrations
{
    public partial class updateParkingSpaceModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "DateCreated",
                table: "ParkingSpace");

            migrationBuilder.DropColumn(
                name: "DateDeleted",
                table: "ParkingSpace");

            migrationBuilder.DropColumn(
                name: "DateModified",
                table: "ParkingSpace");

            migrationBuilder.DropColumn(
                name: "IsActive",
                table: "ParkingSpace");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "DateCreated",
                table: "ParkingSpace",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "DateDeleted",
                table: "ParkingSpace",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "DateModified",
                table: "ParkingSpace",
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "IsActive",
                table: "ParkingSpace",
                nullable: false,
                defaultValue: false);
        }
    }
}
