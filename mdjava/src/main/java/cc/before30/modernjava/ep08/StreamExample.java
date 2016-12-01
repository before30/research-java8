package cc.before30.modernjava.ep08;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by before30 on 02/12/2016.
 */
// https://blog.jooq.org/2015/12/08/3-reasons-why-you-shouldnt-replace-your-for-loops-by-stream-foreach/
public class StreamExample {
    public static void main(String[] args) {
//        for
//            if continue
//            if continue
//            for
//                if available
//                    for
        List<Role> roles = getRoles(10);

        int count1 = 0;
        System.out.println("================");
        for ( Role role : roles) {
            if (role.id < 5) {
                count1++;
                continue;
            } else {
                count1++;
            }

            if (role.id > 7) {
                count1++;
                continue;
            } else {
                count1++;
            }

            for (Vip vip: role.vips) {
                if (vip.available) {
                    count1++;
                    for (Domain domain : vip.domains) {
                        count1++;
                        System.out.println(domain.toString());
                    }
                } else {
                    count1++;
                }
            }
        }

        System.out.println("=================");
        final AtomicInteger count2 = new AtomicInteger(1);

        roles.stream()
                .filter(r -> {
                    count2.getAndAdd(1);
                    return !(r.id < 5);
                })
                .filter(r -> {
                    count2.getAndAdd(1);
                    return !(r.id > 7);
                })
                .forEach(r -> {
                        count2.getAndAdd(1);
                        r.vips.stream()
                            .filter(vip -> {
                                count2.getAndAdd(1);
                                return vip.available;
                            })
                            .forEach(vip -> {
                                count2.getAndAdd(1);
                                vip.domains.stream()
                                        .forEach(v -> {
                                            count2.getAndAdd(1);
                                            System.out.println(v);
                                        });
                            });
                     }
                );

        System.out.println("==========");

        final AtomicInteger count3 = new AtomicInteger(1);
        roles.stream()
                .filter(r -> {
                    count3.getAndAdd(1);
                    return !(r.id < 5);
                })
                .filter(r -> {
                    count3.getAndAdd(1);
                    return !(r.id > 7);
                })
                .flatMap(role -> {
                    count3.getAndAdd(1);
                    return role.vips.stream();
                })
                .filter(vip -> {
                    count3.getAndAdd(1);
                    return vip.available;
                })
                .flatMap(vip -> {
                    count3.getAndAdd(1);
                    return vip.domains.stream();
                })
                .forEach(v -> {
                    count3.getAndAdd(1);
                    System.out.println(v);
                });


        System.out.println("for " + count1);
        System.out.println("stream " + count2);
        System.out.println("w flatmap " + count3);
    }

    static List<Role> getRoles(int size) {
        Random random = new Random(System.currentTimeMillis());
        return IntStream.rangeClosed(0, size)
                .boxed()
                .map(x -> new Role(x, getVips("id_" + x, random.nextInt(5))))
                .collect(Collectors.toList());
    }

    static List<Vip> getVips(String baseName, int size) {
        Random random = new Random(System.currentTimeMillis());
        return IntStream.rangeClosed(0, size)
                .boxed()
                .map(x -> new Vip(random.nextBoolean(), getDomains(baseName + "_" + x + "_", random.nextInt(5))))
                .collect(Collectors.toList());
    }

    static List<Domain> getDomains(String baseName, int size) {
        return IntStream.rangeClosed(0, size).boxed().map(x -> new Domain(baseName + "_domain_" + x)).collect(Collectors.toList());
    }

    @AllArgsConstructor
    static class Role {
        final int id;
        final List<Vip> vips;

    }

    @AllArgsConstructor
    static class Vip {
        final boolean available;
        final List<Domain> domains;
    }

    @AllArgsConstructor
    @ToString
    static class Domain {
        final String name;
    }

}
