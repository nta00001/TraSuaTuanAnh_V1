using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace WebAPIShoping.Migrations
{
    /// <inheritdoc />
    public partial class changeVoucher : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "EndDate",
                table: "VoucherDb",
                newName: "ExpirationDate");

            migrationBuilder.AddColumn<Guid>(
                name: "VoucherId",
                table: "Orders",
                type: "char(36)",
                nullable: true,
                collation: "ascii_general_ci");

            migrationBuilder.CreateIndex(
                name: "IX_Orders_VoucherId",
                table: "Orders",
                column: "VoucherId");

            migrationBuilder.AddForeignKey(
                name: "FK_Orders_VoucherDb_VoucherId",
                table: "Orders",
                column: "VoucherId",
                principalTable: "VoucherDb",
                principalColumn: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Orders_VoucherDb_VoucherId",
                table: "Orders");

            migrationBuilder.DropIndex(
                name: "IX_Orders_VoucherId",
                table: "Orders");

            migrationBuilder.DropColumn(
                name: "VoucherId",
                table: "Orders");

            migrationBuilder.RenameColumn(
                name: "ExpirationDate",
                table: "VoucherDb",
                newName: "EndDate");
        }
    }
}
