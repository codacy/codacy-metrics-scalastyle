# codacy-metrics-scalastyle

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/88b2abcf178c4d50a3e2b2a49b5fc32a)](https://www.codacy.com/gh/codacy/codacy-metrics-scalastyle?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=codacy/codacy-metrics-scalastyle&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/88b2abcf178c4d50a3e2b2a49b5fc32a)](https://www.codacy.com/gh/codacy/codacy-metrics-scalastyle?utm_source=github.com&utm_medium=referral&utm_content=codacy/codacy-metrics-scalastyle&utm_campaign=Badge_Coverage)
[![CircleCI](https://circleci.com/gh/codacy/codacy-metrics-scalastyle.svg?style=svg)](https://circleci.com/gh/codacy/codacy-metrics-scalastyle)
[![Docker Version](https://images.microbadger.com/badges/version/codacy/codacy-metrics-scalastyle.svg)](https://microbadger.com/images/codacy/codacy-metrics-scalastyle "Get your own version badge on microbadger.com")

This is the docker engine we use at Codacy to get Scala complexity.

## Usage

You can create the docker by doing:

```bash
./scripts/publish.sh
```

The docker is ran with the following command:

```bash
docker run -it -v $srcDir:/src  <DOCKER_NAME>:<DOCKER_VERSION>
docker run -it -v $PWD/src/test/resources:/src codacy/codacy-metrics-scalastyle:latest
```

## Test

Run the tests:

```bash
./scripts/test.sh
```

## What is Codacy

[Codacy](https://www.codacy.com/) is an Automated Code Review Tool that monitors your technical debt, helps you improve your code quality, teaches best practices to your developers, and helps you save time in Code Reviews.

### Among Codacy’s features

- Identify new Static Analysis issues
- Commit and Pull Request Analysis with GitHub, BitBucket/Stash, GitLab (and also direct git repositories)
- Auto-comments on Commits and Pull Requests
- Integrations with Slack, HipChat, Jira, YouTrack
- Track issues in Code Style, Security, Error Proneness, Performance, Unused Code and other categories

Codacy also helps keep track of Code Coverage, Code Duplication, and Code Complexity.

Codacy supports PHP, Python, Ruby, Java, JavaScript, and Scala, among others.

### Free for Open Source

Codacy is free for Open Source projects.
