use veterinaria;
db.createUser(
{
	user: "usuario_owner",
	pwd: "usuario_password",
	roles: [ { role: "userAdmin", db: "usuariodb" }]
});
