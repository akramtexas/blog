# BLOG overview

[![Build Status](https://api.travis-ci.org/BayesianLogic/blog.svg?branch=master)](https://travis-ci.org/BayesianLogic/blog)

**Bayesian Logic** (BLOG) is a probabilistic modeling language.
It is designed for representing relations and uncertainties among
real-world objects. For instance, tracking multiple targets in a
video. BLOG makes it easy and concise to represent:

- uncertainty about the existence (and the number) of underlying objects
- uncertain relations among objects
- dependencies among relations and functions
- observed evidence.


# Using BLOG

- Requirements: Java 1.6 or above (1.7 or above preferred)
- [User manual](docs/content/pages/user-manual.md)


# Compiling

- For Linux/Mac:
```
  sbt/sbt compile
  sbt/sbt stage
```
- For Windows:
```
  sbt\sbt compile
  sbt\sbt stage
```

# Developer

## Please read first: [Developer's guide](docs/content/pages/develop-guide.md)

## Files
- `blog` main execution file to run the engine
- `dblog` main execution file to run the engine with particle filter (alternative can use blog)
- `parse.sh` to check the syntax of a blog file (very useful for debugging)
- `docs/content/pages/release-note.md` contains all release information and major changes


## Syntax highligher for editors

- sublime: see instruction under `tools/blog-for-sublime`
- emacs: see `tools/blog.el`
- vim: see `tools/blog.vim`
- latex pdf: `blog_py_lexer`, requires a python library `pygments`, and a latex package `minted`


## Working with Eclipse

- Generate eclipse project files
```
  sbt/sbt eclipse
```


## Package and Release

```
  sbt/sbt universal:package-bin
```


## Web server

All files for web engine are under web. It requires `python-webpy` package. See `web/README.md`.
To run the engine, please follow the instructions in `web/README.md`.


Readme Updated: July 2, 2014
