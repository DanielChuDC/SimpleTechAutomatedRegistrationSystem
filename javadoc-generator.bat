@echo off
javadoc -d ./doc-html -author -private -noqualifier all -version -cp ".;.\javax\mail.jar;" cx2002grp2.stars cx2002grp2.stars.util cx2002grp2.stars.functions cx2002grp2.stars.dataitem cx2002grp2.stars.database cx2002grp2.stars.dataconverter
echo ==============Generation Finished==============
echo Checkout ./doc-html for generated documentation
pause
