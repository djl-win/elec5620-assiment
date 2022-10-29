package com.group3.dto;

import com.group3.domain.Log;
import com.group3.domain.Nft;
import lombok.Data;

@Data
public class TransactionInfo {

    private Log log;

    private Nft nft;
}
