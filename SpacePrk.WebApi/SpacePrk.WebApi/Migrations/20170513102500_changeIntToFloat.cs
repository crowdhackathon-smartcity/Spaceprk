using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace SpacePrk.WebApi.Migrations
{
    public partial class changeIntToFloat : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<float>(
                name: "AvailableSpace",
                table: "ParkingSpace",
                nullable: false,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "AvailableSpace",
                table: "ParkingSpace",
                nullable: false,
                oldClrType: typeof(float));
        }
    }
}
