# file-storage-service

## Description
The service allows to store files, keep track of existing files and retrieve them from the file system. </br>
This implementation should allow the management of up to an order of 10^14 files unless the number </br>
of bits to use from the filename hashcode in the distribution of slaves is increased. </br>
Ex: DistributedMachineAllocator.getInstance().setNbBitsMask(val)
## Endpoints
###Create operation
Method: POST</br>
URL: /api/v1/files</br>
BODY: {"file":{"name":"filecreatedfrompostmanclient"}, "content":"asdadsadasda"}</br>
###Read operation
Method: GET</br>
URL: /api/v1/files/{filename}</br>
BODY: </br>
###Update operation
Method: PUT</br>
URL: /api/v1/files/{filename}</br>
BODY: {"file":{"name":"filecreatedfromclient"}, "content":"1311"}</br>
###Delete operation
Method: DELETE</br>
URL: /api/v1/files/{filename}</br>
BODY: </br>
###Enumerate operation
Method: GET</br>
URL: /api/v1/files/find/{searchWord}</br>
BODY: </br>
###Size operation
Method: GET</br>
URL: /api/v1/files/size</br>
BODY: </br>
## Testing
! running the tests will actually create a file structure on the file system (12 files of ~36 bytes)</br>
! the files are created in the root of the working directory. To change the location, edit the constant DistributedMachine.ROOT</br>
- The tests in the package <i>com.home.assignment.controller</i> are integration tests </br>
- The tests in the other packages are unit tests</br>
- All tests use the same file directory structure on the disk as the working server
## Possible improvements
- use a configuration file to collect all needed configurations (folder where to store the files on the fs, ...)
- serialize the structure that keeps the file names in memory so that in case of jvm stop, we don't loose the references to the files
- have separate services that run on different machines to keep the file names list and store the files on disk. </br>
In the current implementation this is simulated by the DistributedMachine class
- dinamically scale for the slave machines and for the file structure. In theory this implementation </br>
should allow scaling for slaves by changing the number of bits to use from the filename hashcode when </br>
distributing the "work" to slaves. Ex: DistributedMachineAllocator.getInstance().setNbBitsMask(val)