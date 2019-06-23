# Restaurante Rústico

Aplicación ficticia creada con Spring MVC, Spring Data, Hibernate, Thymeleafm, Bootstrap y PostgreSQL

Se manejan los módulos de:
* Autenticación
* Sucursales
* Empleados por sucursal

Para probar la autenticación se puede con los siguientes usuarios:
* usuario: admin ; contraseña: admin
* usuari: fdommerque1; contraseña rQf0lS16
* Otros 9 usuarios que se pueden consultar en la base de datos.

Se manejan sesiones. Es decir, si se intenta acceder a la lista de sucursales y no está autenticado, lo redireccionará al login, y lo mismo para cada acción.
De igual forma, si se intenta acceder a la pantalla de login estando autenticado, redirecciona a la lista de sucursales.

Cuenta con un pequeño navbar que simplemente tiene la opción de cerrar sesión.

Se incluye el backup de la base de datos en la raíz con el nombre: **tareaNCapas.backup**