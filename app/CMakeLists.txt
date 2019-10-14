cmake_minimum_required(VERSION 3.4.1)

project("jni")

set(distribution_DIR "${CMAKE_CURRENT_SOURCE_DIR}/result")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

add_library(darkhttpd SHARED IMPORTED)

set_target_properties(darkhttpd PROPERTIES IMPORTED_LOCATION
    ${CMAKE_CURRENT_SOURCE_DIR}/../native/result/lib/${ANDROID_ABI}/libdarkhttpd.so
)

aux_source_directory(code/jni JNI_SRC)

add_library(core SHARED ${JNI_SRC})

#set_target_properties(core
#                      PROPERTIES
#                      LIBRARY_OUTPUT_DIRECTORY
#                     "${distribution_DIR}/lib/${ANDROID_ABI}"
#)

target_include_directories(core PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/../native/result/include
    ${CMAKE_CURRENT_SOURCE_DIR}/code/jni/include
)

target_link_libraries(core
	              darkhttpd
                      log
)
