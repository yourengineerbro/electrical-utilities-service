### How to run:

PreRequisites:

1. Git Bash Installed.
2. Docker Desktop (Running Docker Engine)

Open Docker Desktop, and wait for the docker engine to start.

and Execute following commands in git bash replacing the email, password
with the values shared to me. 
Docker will pick that up from environment and pass to the application.
so that application can use those credentials to serve the requests.


```
# 1. Clone the repository

git clone https://github.com/yourengineerbro/electrical-utilities-service.git

# 2. navigate to the clone repo.

cd electrical-utilities-service

# 3. Set your environment variables

export URJA_API_EMAIL={ENTER_EMAIL_THAT_YOU_SHARED_TO_ME}
export URJA_API_PASSWORD={ENTER_PASSWORD_THAT_YOU_SHARED_TO_ME} 

# Note: exclude the {} while executing above commands. For example,
# export URJA_API_EMAIL=myemail@urja.tech 

# 4. Build and start the container: 
docker compose up --build

```
After waiting for the docker to start the container, once application gets started completely:

 - Go to http://localhost:8080/swagger-ui/index.html

  For swagger-ui, and to see the service in action.


> **PS:** if you see String: " Tomcat started on port 8080 (http) with context path '/' " in the logs of docker command, that means service has started successfully.

---

### Assumptions:

- We will be serving the latest data to the client, without any possibility of serving stale data; for this purpose data is being returned to the client after processed 

directly from the original source (Having the latest data). This assumption is made due to assuming a critical need of the product. 

 *Future Scope:* Data can be reconciled every 24 hours via a cron job to serve directly from application DB instead of relying on third party APIs for every call everytime. 

- Client will be making / seeking the exact response type of the data as he is currently getting from legacy portals. Hence, for smooth transition request and 
response structure are kept same as of legacy portal.

- No assumption is made with public signing key. It is assumed that it is liable to be changed in the future, hence kept the development while keeping this in mind.

--- 

### Design Decision & Trade-offs:

- Using Adapter Pattern to accommodate for any upcoming another third party API to retrieve the same set of data.
- Using modular monolith (a separate module left for auth service -- to make call to our new API gated with login wall).
- While DB call can result into faster response reducing the latency and network overhead, it is traded off right now to use 3rd party API directly for latest data.

---

### What I'd improve or do differently with more time :


- Add an auth service. that will facilitate in keeping track of 'who call--which request'. That will help in giving expirable api-keys for programmatic access. Also, lastly user management (engineers who will be using the new portal). 

- Build more fancy APIs. For example, to search nearest meters, nearest transformers, all meters under some particular transformer(using its unique key).

- A nice clean UI to display data in revealing form. (To quickly draw out information/conclusions out of it).
  
- Add observability(logs and metrics), rate limiting.

- Write unit and integration tests for all modules.

- Retry mechanism to call 3rd party APIs with global error handling.

- multiple export format for the meters' data. (JSON, CSV)


### Related:

Find my reflection on the project by [clicking here.](/REFLECTION.md) or navigating to REFLECTION.md yourself.

Find my initial scanning of the portal to get to know what I have to build: [click here](/PROTOCOL.md) or navigate to PROTOCOL.md

Find the final API contract (OpenAPI 3.x) listing all APIs and the schema of responses: [click here](/openapi.json).








 
