package service;

import model.Client;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClientService {

    public static List<Client> mapToClient(List<String> clientList) {
        return Objects.nonNull(clientList)
                ? clientList.stream().filter(sClient -> sClient.startsWith(Client.CLIENT_CODE))
                .map(ClientService::mapToClient).collect(Collectors.toList())
                : null;
    }

    public static Client mapToClient(String sClient) {
        if (Objects.nonNull(sClient)) {
            Pattern pattern = Pattern.compile(PatternService.getClientRowPattern());
            Matcher matcher = pattern.matcher(sClient);

            if (matcher.matches()) {
                Client clt = new Client();

                clt.setCnpj(matcher.group(1));
                clt.setNome(matcher.group(2));
                clt.setBusinessArea(matcher.group(3));

                return clt;
            }
        }

        return null;
    }
}