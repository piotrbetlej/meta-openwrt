INSANE_SKIP_${PN} += "dev-so installed-vs-shipped file-rdeps dep-cmp build-deps"

DESCRIPTION = "Generalized Wireless Information Library (iwinfo)"
HOMEPAGE = "http://wiki.openwrt.org/doc/howto/wireless.utilities"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://github.com/piotrbetlej/iwinfo.git;branch=master;"
		   
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

PR="r1"

DEPENDS = "libnl libubox"
PACKAGES += "libiwinfo"
RDEPENDS_${PN} = "libiwinfo libubox libnl-genl"

FILES_${PN} = "/usr/bin"
FILES_${PN}-dev = "/usr/include /usr/lib/libiwinfo.so"
FILES_libiwinfo = "/usr/lib/libiwinfo.so.0.0 /usr/lib/libiwinfo.so.0 /usr/lib/libiwinfo.so"

EXTRA_OEMAKE = "IWINFO_BACKENDS=nl80211"

do_compile () {
	oe_runmake 'FPIC=-fPIC -I${B}/../recipe-sysroot/usr/include/libnl3 -I${B}/../recipe-sysroot/usr/include' compile
}

do_install () {
	mkdir -p ${D}/${libdir} ${D}/usr/bin
	install ${B}/libiwinfo.so.0.0 ${D}/${libdir}
	cp -a ${B}/libiwinfo.so.0 ${D}/${libdir}
	cp -a ${B}/libiwinfo.so ${D}/${libdir}
	cp -a ${B}/include ${D}/usr/
	install ${B}/iwinfo ${D}/usr/bin
}