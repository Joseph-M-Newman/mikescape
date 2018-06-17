@echo off
title Cache Packer
echo Packing cache...
"C:\Program Files\Java\jre7\bin\java.exe" -Xmx1024m -cp bin; com.alex.tools.clientCacheUpdater.CacheEditor
pause