Text Analysis
=============

Brief
-----
API for all kind of text-mining features.

Features
--------
Currently in development (v 0.2), only providing :

- Model for raw text document and corpus
- corpus and document parsers (only .txt & html implementation for documents and folder, zip & epub for corpus)
- segmented document model
- text splitters
- several mappers to process documents (raw and segmented)
- Model for documents*tokens matrix
- Some matrix builder (tf-idf and log-entropy weighting implementation)
- basic LSA algorithm (still bugged for querying, Lanczos algorithm missing for performance)

ToDo List
---------
- several type of documents (rtf, odt, doc(x), md, epub) and corpus (rar & tar)
- web-app with RestFull access
- persistence layer
- caching of some data
- multi-thread support
- chaining process 