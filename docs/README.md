# Run this site locally

Launch Jekyll in a docker container:
```bash
docker run --rm --volume="$PWD:/srv/jekyll" -p 4000:4000 -it jekyll/builder:3.8 bash
```

Then, inside the docker container, run:
```bash
# do this once
bundle install
# then run the server
bundle exec jekyll serve --host 0.0.0.0 --verbose --config "_config.yml,_config_dev.yml"
```

The local site will be available at http://localhost:4000