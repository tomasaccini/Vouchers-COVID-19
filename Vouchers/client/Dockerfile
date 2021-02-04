FROM node:15.8.0

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

# Install dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install 

# Copy the app
COPY . ./

EXPOSE 3030

CMD ["npm", "start"]
