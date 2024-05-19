# Search

using ZincSearch
- https://zincsearch-docs.zinc.dev/

## Run
```bash
make start
```
http://localhost:4080

## Import sample
```bash
curl -L https://github.com/zincsearch/zincsearch/releases/download/v0.1.1/olympics.ndjson.gz -o olympics.ndjson.gz
gzip -d  olympics.ndjson.gz 
```
bulk insert
```bash
curl http://localhost:4080/api/_bulk -i -u admin --data-binary "@olympics.ndjson"
```