# dev nest

This system is a collaboration platform designed to connect game developers, studios, and investors. Users can create profiles, showcase their skills, join studios, and apply for project roles that match their expertise. Studios can create projects, define positions, recruit talent, and manage their teams. Investors can explore projects, submit investment requests, and fund the ones they believe in. Through structured workflows—such as project applications, studio invitations, and investment approvals—the platform provides a complete ecosystem that supports the creation, management, and growth of game development teams and their projects.

# team responsibilities:
## osama:
### entity:
user 

admin

skills

platform

genre

project request

project position

project member

### endpoints:
user assign skill

admin approve/reject investor

assign members to projects

get project based on engine

get project based on platform

get project based on genre

update project status

get project available positions

DTO (4 in 3 out)

get project members

get investor with most funded projects

approve reject project requests

relations (fix old relations)

over all testing

presentation (3 slides)

## mohammed:
### entity:
studio

project

studio member

post

### endpoints:
get project by status

get projects by studio

get project based on scope

get project based on estimated budget

get games release this year

get studios with most projects created sorted

### n8n:

send-request-project-email (Webhook6) – Sends a project invitation email to a user when a leader requests them to join a project

generate-welcome-email (Webhook8) – Uses AI to generate a creative HTML welcome email and sends it to a newly registered user

send-accepted-request-email (Webhook9) – Uses AI to generate and send an acceptance email when a user is approved to join a project

send-rejected-request-email (Webhook10) – Uses AI to generate and send a polite rejection email when a user is declined from a project

send-accepted-request-email-investor (Webhook) – Notifies an investor by email that they have been accepted into a project

send-Investor-request-project-email (Webhook1) – Sends an email to the project leader when an investor requests to join a project

send-rejected-request-email-investor (Webhook3) – Informs an investor via email that their investment request was rejected

investor-welcome-email (Webhook2) – Sends a welcome email to a newly registered investor on the platform

send-request-user-email (Webhook11) – Uses AI to generate and send a project invitation email from a leader to a specific user

send-accepted-project-request-email (Webhook12) – Notifies the project leader that a user has accepted the project invitation

send-rejected-project-request-email (Webhook13) – Notifies the project leader that a user has rejected the project invitation

and service webhook for each one

overall testing

relation

presentation (3 slides)

## leen:
### entity:

user request

investor request

investor

### endpoints:

findUserbySkill 

Get funded projects by InvestorId

Get funded projects by StudioId

Get user request by userId

Get user request by studioId  

Get Investor request by investorId 

Get Investor request by projectId

get user of experience high than x

get studios members

get users with specific skill

get users based on role

get user based on country

get user of experience high than x

openAI

relation

presentation (3 slides)
