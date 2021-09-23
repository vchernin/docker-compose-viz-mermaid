
Given the following docker-compose:
```yaml
{% include generated/{{ include.name }}.yaml %}
```

The result will be:

{% include generated/{{ include.name }}-default.md %}

Or as image:

<image src="{{ site.baseurl }}/assets/generated/{{ include.name }}-default.svg" class="img-responsive" />
<image src="{{ site.baseurl }}/assets/generated/{{ include.name }}-dark.svg" />