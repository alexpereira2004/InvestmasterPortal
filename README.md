Springboot pode ajudar a criar imagens Docker sem a necessidade de escrever toda a configuração. Para isso, basta adicionar 
o nome da imagem no arquivo pom.xml.

- build
  - plugins
    - plugin
        - <groupId>org.springframework.boot</groupId>
        - <artifactId>spring-boot-maven-plugin</artifactId>
            -   configuration
                - image
                    - name

Executar o seguinte comando para criar a imagem:

`$ mvn spring-boot:build-image`

Para iniciar o container:

`$ docker run -p 13001:12001 investmaster/portal`

<h3>Swagger</h3>

http://localhost:12001/swagger-ui/index.html#/


