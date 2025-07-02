FROM node:18-alpine

WORKDIR /app

COPY . .

RUN npm install

RUN npm run build

# Install a simple static file server
RUN npm install -g serve

EXPOSE 3000

CMD ["serve", "-s", "dist"]
