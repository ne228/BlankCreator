FROM node:lts-alpine3.20

RUN yarn -v 

COPY . ./app

WORKDIR /app

RUN yarn

ENTRYPOINT [ "yarn", "dev", "--host"]