# Book Api
A simple spring boot web project with database access

## Guide
Start by looking at `BookApi.config`. This is where all configurations are loaded before startup, of note:
* Environment variables (set in `resources/application.yml`)
* The database
* All services used by the application

Once the app is up, you can hit the `controller/BookController` endpoint.

This will access the `BookService`, which will read data from the `BookRepository`

The `BookController` then returns the obtained books

## TODO

Currently the data is set up to talk to a different database of `categories`,
which won't work. Move it to the books db, just follow the trail from the controller,
and change settings in `application.yml`.