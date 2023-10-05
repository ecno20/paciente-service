db.createCollection('pacientes', {
  validator: {
    $jsonSchema: {
      required: ['nombre'],
      properties: {
        nombre: {
          type: 'string',
          description: 'nombre del paciente'
        }
      }
    }
  }
});
db.pacientes.createIndex( { nombre: 1 }, { unique: true } );
