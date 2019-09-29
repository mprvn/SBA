#stage - 1
FROM node:latest as node
LABEL maintainer="mln.prvn@gmail.com"
WORKDIR /app
COPY . .
RUN npm install
RUN npm install node-sass@latest
RUN npm rebuild node-sass

RUN npm run build 

#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/taskmanager /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

