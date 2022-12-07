Start Dockerfile

```bash
cd day7test/root
tree -s .     # Print tree
du -hb .      # Find dir sizes recursively

du -hb . > out.txt
```

From host's terminal:
```bash
docker cp <container-id>:/day7test/root/out.txt ./out.txt
```
