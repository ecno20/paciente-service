# Utiliza una imagen base de MongoDB oficial
FROM mongo:latest
COPY veterinariadb_creation.js /docker-entrypoint-initdb.d/
#COPY load_collection_data.js /docker-entrypoint-initdb.d/
# Mantenimiento
LABEL maintainer="Jonathan Díaz"<jdsmatemaster@gmail.com>
# Expone el puerto de MongoDB (27017 por defecto)
EXPOSE 27017
EXPOSE 8084
# Configuraciones adicionales
# COPY ./mongodb-config-file.conf /etc/mongod.conf
# Directorio de trabajo
WORKDIR /data
# Comando para iniciar MongoDB
CMD ["mongod"]
