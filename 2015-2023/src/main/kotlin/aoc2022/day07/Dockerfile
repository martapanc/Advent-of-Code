FROM ubuntu:kinetic-20220801
RUN apt-get update && apt-get install tree
RUN mkdir day7test
COPY assets/day7script_test.sh /day7test/script_test.sh
COPY assets/day7script.sh /day7test/script.sh
RUN cd day7test && chmod +x script.sh && chmod +x script_test.sh
