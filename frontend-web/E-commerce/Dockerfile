FROM node:18-alpine3.20 as build

#docker build -t frontend .

WORKDIR /app
COPY . .
RUN npm ci
RUN npm install -g @angular/cli
RUN npm run build

FROM nginx:1.27.1-alpine as deploy
#COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/e-commerce /usr/share/nginx/html
EXPOSE 80


