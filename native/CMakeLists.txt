cmake_minimum_required(VERSION 3.4.1)

project(darkhttpd)

set(CMAKE_VERBOSE_MAKEFILE on)

set(distribution_DIR "${CMAKE_CURRENT_SOURCE_DIR}/result")

aux_source_directory(code DARKHTTPD_SRC)

add_library(darkhttpd SHARED ${DARKHTTPD_SRC})

set_target_properties(darkhttpd
                      PROPERTIES
                      LIBRARY_OUTPUT_DIRECTORY
                     "${distribution_DIR}/lib/${ANDROID_ABI}"
)

add_custom_command(TARGET darkhttpd POST_BUILD
                   COMMAND "${CMAKE_COMMAND}" -E
                   copy "${CMAKE_CURRENT_SOURCE_DIR}/code/darkhttpd.h"
                  "${distribution_DIR}/include/darkhttpd.h"
                   COMMENT "Copying darkhttpd to output directory"
)
