#language: es

Característica: Prueba api

  Escenario: Crear usuario
    Cuando el analista consume el recurso crear usuario
      | name  | job  |
      | Jaime | roba |
    Entonces el deberia ver en el codigo de estado 201
    Y validar el nombre

