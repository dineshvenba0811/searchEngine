# Stage 1: Build Vue (Vite) App
FROM node:18-alpine AS builder

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the project and build it
COPY . .
RUN npm run build

# Stage 2: Serve the Built Files Using Nginx
FROM nginx:alpine

WORKDIR /usr/share/nginx/html

# Remove default nginx static files
RUN rm -rf ./*

# Copy built files from the builder stage
COPY --from=builder /app/dist .

# Copy custom Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose Nginx port
EXPOSE 8081

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]