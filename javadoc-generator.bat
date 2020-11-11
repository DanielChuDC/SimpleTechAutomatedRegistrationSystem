@echo off
javadoc -d ../doc-html -author -private -noqualifier all-version -cp ".;.\javax\mail.jar;" cx2002grp2.stars cx2002grp2.stars.util cx2002grp2.stars.data cx2002grp2.stars.functions cx2002grp2.stars.data.dataitem cx2002grp2.stars.data.database cx2002grp2.stars.data.converter
echo ==============Generation Finished==============
echo Checkout ../doc-html for generated documentation
pause