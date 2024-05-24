# OdontoFlow

OdontoFlow é uma API de gestão para clínicas de odontologia. 
A aplicação permite o gerenciamento de profissionjsonais, pacientes, histórico de pacientes, procedimentos e disponibilidades de profissionais.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- H2 Database
- Swagger

## Arquitetura

A aplicação é dividida em domínios, cada um representando uma entidade do sistema.
Cada domínio possui sua própria lógica de negócios e é responsável por gerenciar seu próprio estado.

### Domínios

- `Professional`: Representa um profissional na clínica. Cada profissional tem um nome, documento, endereço, tipo, uma lista de horários disponíveis e uma lista de procedimentos que pode realizar.
- `Scheduling`: Representa um agendamento na clínica.
- `Patient`: Representa um paciente na clínica.
- `PatientRecord`: Representa um registro odontológico de um paciente da clínica. Uma lista de PatientRecord forma o histórico de um paciente.
- `Procedure`: Representa um procedimento que pode ser realizado na clínica.

## Endpoints

A API possui vários endpoints para gerenciar os domínios. Aqui estão alguns exemplos:

- `POST /professional/create`: Cria um novo profissional. Espera um objeto `ProfessionalFormDTO` no corpo da requisição.
- `GET /professional/list`: Retorna a lista de todos os profissionais.
- `PUT /professional/update/{id}`: Atualiza os detalhes de um profissional específico. Espera um objeto `ProfessionalFormDTO` no corpo da requisição.

- `POST /patient/create`: Cria um novo paciente. Espera um objeto `PatientFormDTO` no corpo da requisição.
- `GET /patient/list`: Retorna a lista de todos os pacientes.
- `PUT /patient/update/{id}`: Atualiza os detalhes de um paciente específico. Espera um objeto `PatientFormDTO` no corpo da requisição.

- `POST /scheduling/create`: Cria um novo agendamento. Espera um objeto `SchedulingFormDTO` no corpo da requisição.  
- `GET /scheduling/list/patient/{id}`: Retorna a lista de todos os agendamentos de um paciente específico, identificado pelo ID. Ele também aceita um parâmetro opcional date para filtrar os agendamentos por uma data específica.  
- `GET /scheduling/list/professional/{id}`: Retorna a lista de todos os agendamentos de um profissional específico, identificado pelo ID. Ele também aceita um parâmetro opcional date para filtrar os agendamentos por uma data específica.  
- `PUT /scheduling/update/{id}`: Atualiza um agendamento específico, identificado pelo ID. Ele espera um objeto `SchedulingFormDTO` no corpo da requisição.  
- `PUT /scheduling/done/{id}`: Marca um agendamento específico como concluído. O agendamento é identificado pelo ID.

- `POST /patient-record/create`: Cria um novo registro de paciente. Espera um objeto `PatientRecordFormDTO` no corpo da requisição.
- `PUT /patient-record/update/{id}`: Atualiza um registro de paciente específico. Espera um objeto `PatientRecordFormDTO` no corpo da requisição.
- `GET /patient-record/list-all`: Retorna a lista de todos os registros de pacientes.
- `GET /patient-record/search/{id}`: Retorna um paciente buscando pelo ID.
- `GET /patient-record/list/patient/{id}`: Retorna a lista de registros de paciente, de acordo com o ID do paciente.
- `GET /patient-record/list/professional/{id}`: Retorna a lista de registros de paciente, de acordo com o ID do profissional.

- `POST /procedure/create`: Cria um novo procedimento. Espera um objeto `ProcedureFormDTO` no corpo da requisição.
- `PUT /procedure/update/{id}`: Atualiza um procedimento específico. Espera um objeto `ProcedureFormDTO` no corpo da requisição.
- `GET /procedure/list`: Retorna a lista de todos os procedimentos.

- `POST /professional-availability/create`: Cria uma nova disponibilidade de profissional. Espera um objeto `ProfessionalAvailabilityFormDTO` no corpo da requisição.
- `PUT /professional-availability/update/{id}`: Atualiza uma disponibilidade de profissional específica. Espera um objeto `ProfessionalAvailabilityFormDTO` no corpo da requisição.
- `GET /professional-availability/list-all`: Retorna a lista de todas as disponibilidades de profissionais.
- `GET /professional-availability/professional/{professionalId}`: Retorna a lista de todas as disponibilidades do profissional de acordo com o ID.
- `GET /professional-availability/date/{date}`: Retorna a lista de todas as disponibilidades para uma data específica.
- `GET /professional-availability/day/{dayOfWeek}`: Retorna a lista de todas as disponibilidades para um dia da semana.
- `GET /professional-availability/hour/{hour}`: Retorna a lista de todas as disponibilidades para uma hora específica.
- `GET /professional-availability/procedure/{id}`: Retorna a lista de todas as disponibilidades para um procedimento específico.

## Acessando a API

### Swagger

Para acessar os endpoints da API via Swagger, acesse o seguinte link quando a aplicação estiver em execução:

```
http://localhost:8080/swagger-ui/index.html#/
```

### Banco de Dados H2

Para acessar o banco de dados H2, acesse o seguinte link quando a aplicação estiver em execução:

```
http://localhost:8080/h2-console
```
- user: 
```
sa

```
- password:
```
password
```
