INSANE_SKIP_${PN} += "dev-so dev-deps installed-vs-shipped file-rdeps dep-cmp build-deps"

DESCRIPTION = "Unified Configuration Interface"
HOMEPAGE = "http://wiki.openwrt.org/doc/techref/uci"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://libuci.c;beginline=1;endline=13;md5=0ee862ed12171ee619c8c2eb7eff77f2"

SRC_URI = "git://nbd.name/uci.git;protocol=git;branch=master"
SRC_URI += "file://uci.sh"
# SRC_URI += "file://luajit.patch"
		   
SRCREV = "556215152a216c179fe2ca7db9b1de7036ceda60"
S = "${WORKDIR}/git"


inherit cmake

PR="r1"

##
PN_remove = "_git"
MAJ = "1"
MIN = "0"
LIBVER = "0"

FULL_NUM = "${MAJ}.${MIN}.${LIBVER}"
SYMLINK1_NUM = "${MAJ}"

LINK_LIBNAME = "lib${PN}.so"
FULL_LIBNAME = "${LINK_LIBNAME}.${FULL_NUM}"
SYMLINK1 = "${LINK_LIBNAME}.${SYMLINK1_NUM}"
##

DEPENDS = "libubox lua5.1"

EXTRA_OECMAKE = "-DLUAPATH=/usr/lib/lua/5.1"
RDEPENDS_${PN} += "libuci"

PACKAGES += "libuci"
FILES_${PN} = "/usr/lib/lua/5.1 /usr/bin /sbin ${base_libdir}/${LINK_LIBNAME}"
FILES_${PN}-dev = "/usr/include /usr/lib/libuci.so.*"
FILES_libuci = "${base_libdir}/libuci.so.${MAJ}*"
FILES_${PN}-dbg += "/usr/lib/lua/5.1/.debug"


do_install_append () {
        mkdir -p ${D}/lib ${D}/lib/config ${D}/sbin

        install ${D}/usr/lib/libuci.so ${D}/${base_libdir}
	
	mv ${D}/${base_libdir}/libuci.so ${D}/${base_libdir}/libuci.so.1.0.0
        ln -rs ${D}/${base_libdir}/libuci.so.1.0.0 ${D}/${base_libdir}/${SYMLINK1}
        ln -rs ${D}/${base_libdir}/${SYMLINK1} ${D}/${base_libdir}/${LINK_LIBNAME} 
	install ${WORKDIR}/uci.sh ${D}/${base_libdir}/config
        install ${D}/usr/bin/uci ${D}/sbin
}
