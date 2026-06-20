# act event payloads

These files simulate the branch rules in `.github/workflows/ci.yml`.

```sh
act push -e .github/events/push-feature.json
act push -e .github/events/push-develop.json
act push -e .github/events/push-main.json

act pull_request -e .github/events/pull-request-develop.json
act pull_request -e .github/events/pull-request-main.json
```

Expected job selection:

- `push-feature.json`: lint only.
- `push-develop.json`: lint and test.
- `push-main.json`: lint, test, build, and docker.
- `pull-request-develop.json`: lint and test.
- `pull-request-main.json`: lint, test, build, and docker.
